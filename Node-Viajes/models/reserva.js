const mongoose=require('mongoose');
const Schema = mongoose.Schema;


const reservaSchema=new Schema ({
    id: Number,
    destino: String,
    nombre: String,
    apellido: String,
    telefono: String,
    email: String,
    fecha: Date,
    hora: Number,
    asiento: Number
})
const Reserva= mongoose.model('Reserva', reservaSchema);

module.exports= Reserva;