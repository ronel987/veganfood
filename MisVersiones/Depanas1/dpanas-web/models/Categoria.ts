import mongoose from 'mongoose';

const CategoriaSchema = new mongoose.Schema({
    productos: [
        {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'Producto',
        },
    ],
    nombre: {
        type: String,
        minlength: 3,
        maxlength: 50,
        required: true,
    },
    fechaRegistro: {
        type: Date,
        default: Date.now,
        required: true,
    },
});

export default mongoose.models.Categoria ||
    mongoose.model('Categoria', CategoriaSchema, 'categorias');
