import mongoose from 'mongoose';

const PedidoSchema = new mongoose.Schema({
    cliente: {
        type: String,
        ref: 'Cliente',
        required: true,
    },
    direccion: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Direccion',
    },
    detalles: [
        new mongoose.Schema({
            producto: {
                type: mongoose.Schema.Types.ObjectId,
                ref: 'Producto',
                required: true,
            },
            precioUnitario: {
                type: Number,
                min: 0,
                required: true,
            },
            cantidad: {
                type: Number,
                min: 1,
                max: 99,
                required: true,
            },
            subtotal: {
                type: Number,
                min: 0,
                required: true,
            },
        }),
    ],
    total: {
        type: Number,
        min: 0,
        required: true,
    },
    fechaRegistro: {
        type: Date,
        required: true,
        default: Date.now,
    },
    estado: {
        type: String,
        enum: ['RECIBIDO', 'EN_PROCESO', 'ENTREGADO'],
        required: true,
        default: 'RECIBIDO',
    },
});

export default mongoose.models.Pedido || mongoose.model('Pedido', PedidoSchema);
