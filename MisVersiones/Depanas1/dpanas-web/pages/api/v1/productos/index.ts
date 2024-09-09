import { NextApiRequest, NextApiResponse } from 'next';
import getConnection from '../../../../database/mongo';
import Producto from '../../../../models/Producto';
import Categoria from '../../../../models/Categoria';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
    await getConnection();

    if (req.method === 'GET') {
        const productos = await Producto.find().populate({
            path: 'categoria',
            model: Categoria,
            select: {
                productos: 0,
            },
        });
        res.status(200).json({ data: productos.map((producto) => producto.toJSON()) });
    } else if (req.method === 'POST') {
        const {
            categoria: categoriaId,
            nombre,
            descripcion,
            precio,
            stock,
            imagenUrl,
            estado,
        } = req.body;
        const categoria = await Categoria.findById(categoriaId);

        if (!categoria) {
            res.status(400).json({ message: 'La categoria no existe' });
            return;
        }

        const producto = await Producto.create({
            categoria: categoria._id,
            nombre,
            descripcion,
            precio,
            stock,
            imagenUrl,
            estado,
        });
        producto.populate({ path: 'categoria', model: Categoria });

        categoria.productos.push(producto._id);
        await categoria.save();

        res.status(201).json({ data: producto.toJSON() });
    } else res.status(405).end(`Method ${req.method} not allowed.`);
}
