const express = require('express')
//import fetch from 'node-fetch';
const fetch = require('node-fetch')
const router = express.Router();
const colors = require('colors');
const Reserva= require('../models/reserva')
const { restart } = require('nodemon');
const { json } = require('express/lib/response');


router.get('/', async (req, res, next) => {

    const urlBtcInfo = 'http://localhost:8081/api/empresa/get/'

    try {
        const request = await fetch(urlBtcInfo)
        const result = await request.json()
        console.log(result)
        res.render('../views/reserva.ejs',{result})
       // console.log("Estoy dentro")
    } catch (error) {

        res.status(400).send(error)
        console.log("no me encuentro")
    }

})

router.get('/crear', async (req, res, next) => {

res.render('../views/crear.ejs')
   
})


router.post('/crear',async(req,res)=>{
    
    
    
   // const urlBtcInfo = 'http://localhost:8081/api/empresa/'
    console.log(req.body)
  
      try{ 
        const request = await fetch("http://localhost:8081/api/empresa/", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json",
                "Accept" : "*/*"
            } ,
            body:JSON.stringify( 
                {
                    fecha: req.body.fecha,
                    destino:  req.body.destino,
                    hora:  req.body.hora,
                    nombre:  req.body.nombre,
                    apellido:  req.body.apellido,
                    telefono:  req.body.telefono,
                    email: req.body.email
                }),
            
            
        
        })

        res.redirect('/api/empresa')

        console.log(request);}
        catch(error){
            console.log(error)
        } 

})


router.get('/:id', async (req,res)=>{

    const id= req.params.id

    try {
      
        const request = await fetch("http://localhost:8081/api/empresa/delete/"+id,{
            method:"DELETE"
        })  
        res.redirect('/api/empresa')

    } catch (error) {
        console.log(error)
    }
})

module.exports = router;