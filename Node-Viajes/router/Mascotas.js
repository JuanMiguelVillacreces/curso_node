const express = require('express');
const router = express.Router();
const Mascota= require('../models/mascota')
router.get('/',async(req,res)=>{

    try{

        const arrayMascotasDB = await Mascota.find()
        //console.log(arrayMascotasDB)
        
        res.render("mascotas",{
            arrayMascotas : arrayMascotasDB
            // arrayMascotas:[
            //     {id:'uno',nombre:'rex',descripcion:'algo'},
            //     {id:'dos',nombre:'aaa',descripcion:'algo'}
            // ]
        })

    } catch (error){
        console.log(error)
    }


  
})

router.get('/crear',(req,res)=>{

    res.render('crear')

})

router.post('/',async(req,res)=>{
    const body=req.body
    console.log(body)
    try {
        // const mascotaDB=new Mascota(body)
        // await mascotaDB.save()
        await Mascota.create(body)//hace lo mismo que lo de arriba 
        res.redirect('/mascotas')

        console.log(mascotaDB)
    } catch (error) {
        console.log(error)
    }
})


router.get('/:id',async(req,res)=>{

    const id= req.params.id

    try {

        const mascotaDB=await Mascota.findOne({_id:id})
        console.log(mascotaDB)

        res.render('detalle',{
            mascota:mascotaDB,
            error:false
        })
        
    } catch (error) {
        console.log(error)
        res.render('detalle',{
            error:true,
            mensaje:'No existe ninguna mascota con ese id'
        })
    }
})


router.delete('/:id', async (req,res)=>{

    const id= req.params.id

    try {
        const mascotaDB = await Mascota.findByIdAndDelete({_id:id})

        if(mascotaDB){
            res.json({
                estado:true,
                mensaje:'eliminado'
            })
        }else {
            res.json({
                estado:true,
                mensaje:'fallo al eliminar'
            })
        }

    } catch (error) {
        console.log(error)
    }
})

module.exports = router;