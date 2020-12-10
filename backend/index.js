let firebase = require('firebase');
let express = require('express')
let app = express()
let port = process.env.PORT || 3000

let bodyParser = require('body-parser')

// let eventsController = require('./controllers/eventsController')
// let societiesController = require('./controllers/societiesController')

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

var firebaseConfig = {
    
};

// Initialize Firebase
firebase.initializeApp(firebaseConfig);
// firebase.analytics();

// make auth and firestore references
const auth = firebase.auth();
const db = firebase.firestore();

// update firestore settings
db.settings({ timestampsInSnapshots: true });

auth.onAuthStateChanged(user => {
    if (user) {
      console.log('user logged in: ', user.email);
    } else {
      console.log('user logged out');
    }
})

app.post('/registerFarmer', async (req, res) => {
    try {
        let email= req.body.email;
        let password=req.body.password ;

        auth.createUserWithEmailAndPassword(email, password).then(cred => {
            
            try {
                let latitude=req.body.latitude;
                let longitude=req.body.longitude;

                db.collection('farmers').add({
                    address: req.body.address,
                    image: req.body.image,
                    location: new firebase.firestore.GeoPoint(latitude, longitude),
                    name: req.body.name,
                    phone: req.body.phone,
                    rating: 0,
                    uid: cred.user.uid,
                    numberOfRating:0,
                }).then((farmer)=>{
                    res.send(farmer.id);
                });

            } catch {
                res.send("Cannot create farmer");
            }
            
        }).catch( error=> {
            res.send(error);
       });

    } catch {
        res.send("Cannot create user");
    }
});

app.post('/registerBuyer', async (req, res) => {
    try {
        let email= req.body.email;
        let password=req.body.password ;

        auth.createUserWithEmailAndPassword(email, password).then(cred => {
            
            try {
                let latitude=req.body.latitude;
                let longitude=req.body.longitude;
        
                db.collection('buyers').add({
                    address: req.body.address,
                    image: req.body.image,
                    location: new firebase.firestore.GeoPoint(latitude, longitude),
                    name: req.body.name,
                    phone: req.body.phone,
                    uid: cred.user.uid,
                }).then((buyer)=>{
                    res.send(buyer.id);
                });

            } catch {
                res.send("Cannot create buyer");
            }
            
        }).catch( error=> {
            res.send(error);
       });

    } catch {
        res.send("Cannot create user");
    }
});

app.post('/login', async (req, res) => {
    try {
        let email= req.body.email;
        let password=req.body.password;

        auth.signInWithEmailAndPassword(email, password).then(cred => {

            uid=cred.user.uid;
            // console.log(uid);
            let farmerList = []
            db.collection('farmers').where('uid','==',uid).get().then(farmers => {
                farmers.docs.forEach(doc => {
                    let data=doc.data();
                    farmerList.push(data);
                });

                let buyerList = []
                db.collection('buyers').where('uid','==',uid).get().then(buyers => {
                    buyers.docs.forEach(doc => {
                        let data=doc.data();
                        buyerList.push(data);
                    });
                    if(farmerList.length>0){
                        res.send({"type":"1","uid":farmerList[0].uid});      
                    }
                    else if(buyerList.length>0){
                        res.send({"type":"0","uid":buyerList[0].uid});      
                    }
                    else{
                        res.send("-1");  
                    }
                }).catch(function(error) {
                    console.log(error);
                }); 
            }).catch(function(error) {
                console.log(error);
            }); 

        }).catch( error=> {
            res.send(error);
       });

    } catch {
        res.send("-1");
    }
});

app.post('/logout', async (req, res) => {
    try {
        auth.signOut().then(()=>{
            res.send("logOut Successfully");
        });

    } catch {
        res.send("Cannot logOut user");
    }
});

app.post('/addCrop', async (req, res) => {
    try {
        let date=Date(Date.now()); 
        let latitude=req.body.latitude;
        let longitude=req.body.longitude;

        db.collection('crop').add({
            farmeruid: req.body.farmeruid,
            image: req.body.image,
            name: req.body.name,
            pricePerTon:req.body.pricePerTon,
            location: new firebase.firestore.GeoPoint(latitude, longitude),
            timeleft:req.body.timeleft,
            views:0,
            createdDate:date,
            weight:req.body.weight
        }).then((crop)=>{
            res.send(crop.id);
        });
    } catch {
        res.send("Cannot create crop");
    }
});

app.post('/addOrder', async (req, res) => {
    try {
        let date=Date(Date.now()); 
        db.collection('order').add({
            cropid: req.body.cropid,
            orderDate: date,
            weight: req.body.weight,
            farmeruid: req.body.farmeruid,
            buyeruid: req.body.buyeruid,
            cancelled:false
        }).then((order)=>{
            res.send(order.id);
        });

    } catch {
        res.send("Cannot create order");
    }
});

app.post('/getCrops', async (req, res) => {
    try {

        farmeruid=req.body.uid;
        let cropsList = []
        db.collection('crop').where('farmeruid','==',farmeruid).get().then(crops => {
            crops.docs.forEach(doc => {
                let data=doc.data();
                cropsList.push(data);
            });

            res.send(cropsList);     
        }).catch(function(error) {
            console.log("Error getting documents: ", error);
            res.send(error);
        }); 

    } catch {
        res.send("Cannot get crop");
    }
});

app.get('/getFarmer', async (req, res) => {
    try {

        uid = req.body.uid;
        db.collection('farmers').where('uid','==',uid).get().then(farmer => {
            farmer.docs.forEach(doc => {
                let data=doc.data();
                res.json(data);
            });
        }).catch(function(error) {
            console.log("Error getting documents: ", error);
            res.json(error);
        }); 

    } catch {
        res.json("Cannot get farmer");
    }
});

app.get('/getBuyer', async (req, res) => {
    try {

        uid = req.body.uid;
        db.collection('buyers').where('uid','==',uid).get().then(buyer => {
            buyer.docs.forEach(doc => {
                let data=doc.data();
                res.json(data);
            });
   
        }).catch(function(error) {
            console.log("Error getting documents: ", error);
            res.json(error);
        }); 

    } catch {
        res.json("Cannot get farmer");
    }
});

app.get('/searchCrops', async (req, res) => {
    try {

        name=req.body.name;
        let cropsList = []
        db.collection('crop').where('name','==',name).get().then(crops => {
            crops.docs.forEach(doc => {
                let data=doc.data();
                cropsList.push(data);
            });

            res.json(cropsList);     
        }).catch(function(error) {
            console.log("Error getting documents: ", error);
            res.json(error);
        }); 

    } catch {
        res.json("Cannot get crop");
    }
});

app.put('/updateCrops', async (req, res) => {
    try {

        db.collection('crop').doc(req.body.id).update({
            pricePerTon: req.body.pricePerTon,
            weight: req.body.weight,
            timeleft: req.body.timeleft
        }).then(()=>{
            res.send("get crop");
        });

    } catch {
        res.send("Cannot get crop");
    }
});

app.put('/addRating', async (req, res) => {
    try {
        let uid = req.body.uid;

        db.collection('farmers').where('uid','==',uid).get().then(farmer => {
            let numberOfRating,rating,id;

            let farmerObject=farmer.docs[0].data();
            
            numberOfRating = farmerObject.numberOfRating;
            rating = farmerObject.rating;
            id = farmer.docs[0].id;

            let new_rating = req.body.rating;
            new_rating = new_rating + numberOfRating*rating;
            new_rating = new_rating/(numberOfRating+1);
            numberOfRating=numberOfRating+1;

            db.collection('farmers').doc(id).update({
                numberOfRating:numberOfRating,
                rating:new_rating

            }).then(()=>{
                res.send("rating updated");
            }).catch(function(error) {
                res.send("Cannot get farmer with this uid");
            });

        }).catch(function(error) {
            res.send("Cannot get farmer with this uid");
        });

    } catch {
        res.send("Cannot get farmer with this uid");
    }
});

app.put('/cancelOrder', async (req, res) => {
    try {

        db.collection('order').doc(req.body.id).update({
            cancelled: true
        }).then(()=>{
            res.send("updated order");
        });

    } catch {
        res.send("Cannot get order");
    }
});

app.get('/getFarmerOrder', async (req, res) => {
    try {

        uid = req.body.uid;
        db.collection('order').where('farmeruid','==',uid).get().then(order => {
            let farmerOrder = []
            order.docs.forEach(doc => {
                let data=doc.data();
                farmerOrder.push(data);
            });

            res.json(farmerOrder);
        }).catch(function(error) {
            console.log("Error getting documents: ", error);
            res.json(error);
        }); 

    } catch {
        res.json("Cannot get farmer");
    }
});

app.get('/getBuyerOrder', async (req, res) => {
    try {

        uid = req.body.uid;
        db.collection('order').where('buyeruid','==',uid).get().then(order => {
            let buyerOrder = []
            order.docs.forEach(doc => {
                let data=doc.data();
                buyerOrder.push(data);
            });

            res.json(buyerOrder);
        }).catch(function(error) {
            console.log("Error getting documents: ", error);
            res.json(error);
        }); 

    } catch {
        res.json("Cannot get farmer");
    }
});

app.get('/getCropByLocation', async (req, res) => {
    try {

        let lat = 0.0144927536231884;
        let lon = 0.0181818181818182;
        let distance = req.body.distance; 
        let latitude = req.body.latitude;
        let longitude = req.body.longitude;

        let lowerLat = latitude - (lat * distance);
        let lowerLon = longitude - (lon * distance);

        let greaterLat = latitude + (lat * distance);
        let greaterLon = longitude + (lon * distance);

        let lesserGeopoint = new firebase.firestore.GeoPoint(lowerLat, lowerLon);
        let greaterGeopoint = new firebase.firestore.GeoPoint(greaterLat, greaterLon);
        
        db.collection("crop").orderBy("location").where("location", ">" ,lesserGeopoint).where("location", "<" , greaterGeopoint).get().then( query=>{
            let queryList = []
            query.docs.forEach(doc => {
                let data=doc.data();
                queryList.push(data);
            });

            res.json(queryList);
        }).catch(function(error) {
            res.json(error);
        });
    } catch {
        res.json("Cannot get Crops");
    }
});

app.put('/updateProfile', async (req, res) => {
    try {

        let uid=req.body.uid;
        db.collection('farmers').where('uid','==',uid).get().then(farmers => {
            fid=-1;
            if(farmers.docs.length){
                fid=farmers.docs[0].id;
                fdata=farmers.docs[0].data();
            }

            db.collection('buyers').where('uid','==',uid).get().then(buyers => {
                bid=-1;
                if(buyers.docs.length){
                    bid=buyers.docs[0].id;
                    bdata=buyers.docs[0].data();
                }
                let latitude=req.body.latitude;
                let longitude=req.body.longitude;
                
                if(fid!=-1){
                    db.collection('farmers').doc(fid).update({
                        address:req.body.address,
                        image:req.body.image,
                        location: new firebase.firestore.GeoPoint(latitude, longitude),
                        phone:req.body.phone
                    }).then(()=>{
                        res.send("profile updated");
                    }).catch(function(error) {
                        res.send("Cannot get farmer with this uid");
                    });     
                }
                
                else if(bid!=-1){
                    console.log(bid);
                    db.collection('buyers').doc(bid).update({
                        address:req.body.address,
                        image:req.body.image,
                        location: new firebase.firestore.GeoPoint(latitude, longitude),
                        phone:req.body.phone
                    }).then(()=>{
                        res.send("profile updated");
                    }).catch(function(error) {
                        res.send("Cannot get buyer with this uid");
                    });    
                }

                else{
                    res.send("-1");  
                }

            }).catch(function(error) {
                console.log(error);
            }); 
        }).catch(function(error) {
            console.log(error);
        }); 

    } catch {
        res.send("-1");
    }
});

app.post('/favourites', async (req, res) => {
    try {

        db.collection('favourites').add({
            farmeruid: req.body.farmeruid,
            buyeruid: req.body.buyeruid
        }).then((favourite)=>{
            res.send(favourite.id);
        });

    } catch {
        res.send("Cannot create favourite");
    }
});

app.delete('/deleteFav',async (req, res) =>{
    db.collection("favourites").doc(req.body.id).delete().then(()=>{
        res.sendStatus(200);
    }).catch(function(error) {
        res.sendStatus(404);
    });    
});

app.get('/getfavourites', async (req, res) => {
    try {

        let uid = req.body.uid
        db.collection("favourites").where("buyeruid", "==" ,uid).get().then( query=>{
            let queryList = []
            query.docs.forEach(doc => {
                let data=doc.data();
                queryList.push(data);
            });

            res.json(queryList);
        }).catch(function(error) {
            res.json(error);
        });
    } catch {
        res.json("Cannot get favourites");
    }
});

app.get('/getAllCrops', async (req, res) => {
    try {

        // farmeruid=req.body.uid;
        let cropsList = []
        db.collection('crop').get().then(crops => {
            crops.docs.forEach(doc => {
                let data=doc.data();
                cropsList.push(data);
            });

            res.json(cropsList);     
        }).catch(function(error) {
            console.log("Error getting documents: ", error);
            res.json(error);
        }); 

    } catch {
        res.json("Cannot get crop");
    }
});

app.post('/getfarmerbycrop', async (req, res) => {
    try {

        name=req.body.name;
        let farmerList = []
        db.collection('crop').where('name','==',name).get().then(async function(crops){
            let itemprocessed=0;
            crops.docs.forEach(async function(doc){
                let uid=doc.data().farmeruid;
                await db.collection('farmers').where('uid','==',uid).get().then(farmer => {
                    if(farmer.docs.length){
                        let data=farmer.docs[0].data();
                        farmerList.push(data);
                    }
                }).catch(function(error) {
                    console.log("Error getting documents: ", error);
                    res.send(error);
                });

                itemprocessed=itemprocessed+1;
                if(itemprocessed === crops.docs.length) {
                    res.send(farmerList);     
                }
            });
        }).catch(function(error) {
            console.log("Error getting documents: ", error);
            res.send(error);
        }); 

    } catch {
        res.send("Cannot get crop");
    }
});



app.listen(port, () => console.log(`Starting http://localhost:${port}`));