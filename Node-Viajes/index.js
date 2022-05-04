const express = require('express') 
const bodyParser = require('body-parser')

const PORT = 3000 //puerto donde corre el server

const app = express()


const adminRoutes = require('./router/RutasWeb') //Importamos nuestro ruta donde hacemos la llamada a la API de bitso

app.use(bodyParser.urlencoded({ extended: false }))

app.use('/api/empresa', adminRoutes)  //Ruta por la que vamos a acceder a nuestro servicio


app.listen(PORT, () => {
    console.log('Service is running ' +  PORT)
}) //Correr nuestro server en el puerto que indicamos en la variable PORT

//motor de plantillas
app.set('view engine','ejs');
app.set('views',__dirname + '/views');


app.use(express.static(__dirname + "/public"))
