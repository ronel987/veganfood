import mongoose from 'mongoose';

const ProductoSchema = new mongoose.Schema({
    categoria: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Categoria',
        required: true,
    },
    nombre: {
        type: String,
        minlength: 3,
        maxlength: 50,
        required: true,
    },
    descripcion: {
        type: String,
        maxlength: 255,
    },
    precio: {
        type: Number,
        min: 0,
        required: true,
    },
    stock: {
        type: Number,
        min: 0,
        required: true,
        default: 0,
    },
    imagenUrl: {
        type: String,
        required: true,
        default: 'https://via.placeholder.com/200x200',
    },
    fechaRegistro: {
        type: Date,
        required: true,
        default: Date.now,
    },
    estado: {
        type: String,
        enum: ['activo', 'inactivo'],
        required: true,
        default: 'activo',
    },
});

export default mongoose.models.Producto || mongoose.model('Producto', ProductoSchema, 'productos');
