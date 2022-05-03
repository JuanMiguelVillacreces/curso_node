const express= require('express');
const res = require('express/lib/response');
const bodyParser=require('body-parser')
const app = express();

app.use(bodyParser.urlencoded({extended:false}))
app.use(bodyParser.json())



const port=3000;

//conexion a base de datos
const mongoose= require('mongoose');

const user='node';
const password='node';
const dbname='veterinaria';
const uri=`mongodb+srv://${user}:${password}@cluster0.3ktvg.mongodb.net/${dbname}?retryWrites=true&w=majority`;
//const uri='mongodb://localhost:27017/test';
mongoose.connect(uri,
{useNewUrlParser:true,useUnifiedTopology:true}
)
    .then(()=>console.log('Base de datos conectada'))
    .catch(e=> console.log(e))

//motor de plantillas
app.set('view engine','ejs');
app.set('views',__dirname + '/views');


app.use(express.static(__dirname + "/public"))

//rutas
app.use('/',require('./router/RutasWeb'));
app.use('/mascotas',require ('./router/Mascotas'));


app.listen(port,()=>{
    console.log('servidro a su servicio en el puerto ',port)
})

app.use((req,res,next)=>{
    res.status(404).render("404",{
        titulo:"404",
        descripcion:"Titulo del sitio web"
    })
})
