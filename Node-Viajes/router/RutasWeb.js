const express = require('express')
const fetch = require('node-fetch')
const router = express.Router();
const colors = require('colors');
const Reserva= require('../models/reserva')

const Mascota= require('../models/mascota')


router.get('/', async (req, res, next) => {

    const urlBtcInfo = 'http://localhost:8081/api/empresa/get'
    
    //const urlBtcInfo = 'https://www.google.com'

    try {


        fetch(urlBtcInfo) .then(res => res.text()) .then(text => console.log(text));

      //  const request = await fetch(urlBtcInfo)
        //const result = await request.json()
        //console.log(request.destino)
       // result.asiento
       // console.log('BTC last: ', result.payload.last.rainbow)
      //  res.status(200).send(request)
        console.log("Estoy dentro")
    } catch (error) {

        res.status(400).send(error)
        console.log("no me encuentro")
    }

})

// router.get('/get',async(req,res)=>{

//     try{

//         const arrayMascotasDB = await Mascota.find()
//         //console.log(arrayMascotasDB)
        
//         res.render("mascotas",{
//            // arrayMascotas : arrayMascotasDB
//             arrayMascotas:[
//                 {id:'uno',nombre:'rex',descripcion:'algo'},
//                 {id:'dos',nombre:'aaa',descripcion:'algo'}
//             ]
//         })

//     } catch (error){
//         console.log(error)
//     }


  
// })

module.exports = router;