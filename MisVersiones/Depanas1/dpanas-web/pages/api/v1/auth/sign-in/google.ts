import { NextApiRequest, NextApiResponse } from 'next';
import getConnection from '../../../../../database/mongo';
import Cliente from '../../../../../models/Cliente';
import Direccion from '../../../../../models/Direccion';
import { verifyGoogleToken, createLoginTokens } from '../../../../../utils/TokenUtils';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
    await getConnection();

    if (req.method === 'POST') {
        const { idToken } = req.body;
        const payload = await verifyGoogleToken(idToken);

        if (payload) {
            let cliente = await Cliente.findById(payload._id).populate({
                path: 'direcciones',
                model: Direccion,
            });

            if (!cliente) {
                cliente = await Cliente.create(payload);
                cliente.populate({ path: 'direcciones', model: Direccion });
            }

            const { accessToken, refreshToken } = createLoginTokens(cliente._id, cliente.email);

            res.status(200).json({
                accessToken,
                refreshToken,
                data: cliente,
            });
        } else res.status(401).end('Unauthorized');
    } else res.status(405).end(`Method ${req.method} not allowed.`);
}
