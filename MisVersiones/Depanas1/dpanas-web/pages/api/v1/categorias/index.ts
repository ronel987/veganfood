import { NextApiRequest, NextApiResponse } from 'next';
import getConnection from '../../../../database/mongo';
import Categoria from '../../../../models/Categoria';
import Producto from '../../../../models/Producto';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
    await getConnection();

    if (req.method === 'GET') {
        const categorias = await Categoria.find().populate({
            path: 'productos',
            model: Producto,
        });
        res.status(200).json({
            data: categorias.map((categoria) => categoria.toJSON()),
        });
    } else if (req.method === 'POST') {
        const { nombre } = req.body;
        const categoria = await Categoria.create({ nombre });
        res.status(201).json({ data: categoria });
    } else {
        res.status(405).end(`Method ${req.method} not allowed.`);
    }
}
