import { NextApiRequest, NextApiResponse } from 'next';
import getConnection from '../../../../database/mongo';
import Cliente from '../../../../models/Cliente';
import Direccion from '../../../../models/Direccion';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
    await getConnection();

    const _id = req.query.id;

    if (req.method == 'GET') {
        const cliente = await Cliente.findById(_id).populate({
            path: 'direcciones',
            model: Direccion,
        });

        if (!cliente) {
            res.status(404).json({ message: 'Cliente no encontrado' });
        }

        res.status(200).json({ data: cliente.toJSON() });
    }

    if (req.method === 'PUT') {
        const { nombre, apellido, telefono } = req.body;
        const cliente = await Cliente.findByIdAndUpdate(
            _id,
            {
                nombre,
                apellido,
                telefono,
            },
            { new: true }
        ).populate({ path: 'direcciones', model: Direccion });

        if (!cliente) {
            res.status(404).json({ message: 'Cliente no encontrado' });
        }

        res.status(200).json({ data: cliente.toJSON() });
    } else res.status(405).send(`Method ${req.method} not allowed`);
}
