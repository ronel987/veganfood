import { NextApiRequest, NextApiResponse } from 'next';
import getConnection from '../../../../database/mongo';
import Producto from '../../../../models/Producto';
import Categoria from '../../../../models/Categoria';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
    await getConnection();

    if (req.method === 'GET') {
        const id = req.query.id || [];
        const productosIds = Array.isArray(id) ? id : [id];
        const productos = await Producto.find({ _id: { $in: productosIds } }).populate({
            path: 'categoria',
            model: Categoria,
            select: {
                productos: 0,
            },
        });
        res.status(200).json({ data: productos.map((producto) => producto.toJSON()) });
    } else res.status(405).end(`Method ${req.method} not allowed.`);
}
