import { NextApiRequest, NextApiResponse } from 'next';
import getConnection from '../../../../database/mongo';
import Cliente from '../../../../models/Cliente';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
    await getConnection();

    if (req.method === 'GET') {
        const clientes = await Cliente.find();
        res.status(200).json({ data: clientes.map((cliente) => cliente.toJSON()) });
    } else res.status(405).send(`Method ${req.method} not allowed`);
}
