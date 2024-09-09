import { NextApiRequest, NextApiResponse } from 'next';
import getConnection from '../../../../database/mongo';
import Pedido from '../../../../models/Pedido';
import Cliente from '../../../../models/Cliente';
import Producto from '../../../../models/Producto';
import Direccion from '../../../../models/Direccion';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
    await getConnection();

    if (req.method === 'GET') {
        const { cliente } = req.query;
        let query = null;

        if (cliente) query = Pedido.find({ cliente });
        else query = Pedido.find();

        const pedidos = await query
            .sort({ fechaRegistro: -1 })
            .populate({ path: 'cliente', model: Cliente, select: { direcciones: 0, pedidos: 0 } })
            .populate({ path: 'direccion', model: Direccion, select: { cliente: 0 } })
            .populate({ path: 'detalles.producto', model: Producto, select: { categoria: 0 } });
        res.status(200).json({ data: pedidos.map((pedido) => pedido.toJSON()) });
    } else if (req.method === 'POST') {
        const { cliente: clienteId, direccion, detalles } = req.body;
        const cliente = await Cliente.findById(clienteId);

        if (!cliente) {
            res.status(400).json({ message: 'El cliente no existe' });
            return;
        }
        const pedido = await new Pedido({ cliente, direccion, detalles });
        await Pedido.populate(pedido, [
            { path: 'cliente', model: Cliente, select: { direcciones: 0, pedidos: 0 } },
            { path: 'direccion', model: Direccion, select: { cliente: 0 } },
            { path: 'detalles.producto', model: Producto, select: { categoria: 0 } },
        ]);

        pedido.detalles.forEach((detalle: any) => {
            const subtotal = detalle.producto.precio * detalle.cantidad;
            detalle.precioUnitario = detalle.producto.precio;
            detalle.subtotal = subtotal;
        });
        const total = pedido.detalles.reduce(
            (total: number, detalle: any) => total + detalle.subtotal,
            0
        );
        pedido.total = Math.ceil(total * 10) / 10;
        await pedido.save();

        cliente.pedidos.push(pedido._id);
        await cliente.save();

        res.status(201).json({ data: pedido.toJSON() });
    } else res.status(405).end(`Method ${req.method} not allowed.`);
}
