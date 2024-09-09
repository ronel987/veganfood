import { NextApiRequest, NextApiResponse } from 'next';
import getConnection from '../../../../database/mongo';
import Categoria from '../../../../models/Categoria';
import Producto from '../../../../models/Producto';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
    await getConnection();

    const _id = req.query.id;

    if (req.method === 'GET') {
        const categoria = await Categoria.findById(_id).populate({
            path: 'productos',
            model: Producto,
        });

        if (!categoria) {
            res.status(404).json({ message: 'Categoria no encontrada' });
        }

        res.status(200).json({ data: categoria.toJSON() });
    } else if (req.method === 'PUT') {
        const { nombre } = req.body;
        const categoria = await Categoria.findByIdAndUpdate(
            _id,
            { nombre },
            { new: true }
        ).populate({ path: 'productos', model: Producto });

        if (!categoria) {
            res.status(404).json({ message: 'Categoria no encontrada' });
        }

        res.status(200).json({ data: categoria.toJSON() });
    } else if (req.method === 'DELETE') {
        const categoria = await Categoria.findByIdAndDelete(_id);
        if (!categoria) {
            res.status(404).json({ message: 'Categoria no encontrada' });
        }

        res.status(200).json({ data: categoria.toJSON() });
    } else res.status(405).end(`Method ${req.method} not allowed.`);
}
