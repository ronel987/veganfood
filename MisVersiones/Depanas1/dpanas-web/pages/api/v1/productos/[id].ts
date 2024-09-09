import { NextApiRequest, NextApiResponse } from 'next';
import getConnection from '../../../../database/mongo';
import Producto from '../../../../models/Producto';
import Categoria from '../../../../models/Categoria';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
    await getConnection();

    const _id = req.query.id;

    if (req.method === 'GET') {
        const producto = await Producto.findById(_id).populate({
            path: 'categoria',
            model: Categoria,
            select: {
                productos: 0,
            },
        });

        if (!producto) {
            res.status(404).json({ message: 'Producto no encontrado' });
        }

        res.status(200).json({ data: producto.toJSON() });
    } else if (req.method === 'PUT') {
        const { categoria, nombre, descripcion, precio, imagenUrl, estado } = req.body;
        const producto = await Producto.findByIdAndUpdate(
            _id,
            {
                categoria,
                nombre,
                descripcion,
                precio,
                imagenUrl,
                estado,
            },
            { new: true }
        ).populate({ path: 'categoria', model: Categoria });

        if (!producto) {
            res.status(404).json({ message: 'Producto no encontrado' });
        }

        res.status(200).json({ data: producto.toJSON() });
    } else if (req.method === 'DELETE') {
        const producto = await Producto.findByIdAndDelete(_id);
        if (!producto) {
            res.status(404).json({ message: 'Producto no encontrado' });
        }

        res.status(200).json({ data: producto.toJSON() });
    } else res.status(405).end(`Method ${req.method} not allowed.`);
}
