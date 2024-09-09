import mongoose from 'mongoose';

const DireccionSchema = new mongoose.Schema({
    cliente: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Cliente',
        required: true,
    },
    nombre: {
        type: String,
        minlength: 3,
        maxlength: 50,
        required: true,
    },
});

export default mongoose.models.Direccion ||
    mongoose.model('Direccion', DireccionSchema, 'direcciones');
