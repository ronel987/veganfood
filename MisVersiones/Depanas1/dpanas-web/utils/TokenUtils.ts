import { NextApiRequest } from 'next';
import { OAuth2Client } from 'google-auth-library';
import jwt from 'jsonwebtoken';

interface TokenPayload {
    _id: string;
    email: string;
}

interface GoogleTokenPayload {
    _id: string;
    nombre: string;
    apellido: String;
    email: string;
    imagenUrl: string;
}

interface LoginTokens {
    accessToken: string;
    refreshToken: string;
}

const { WEB_CLIENT_ID, ACCESS_TOKEN_SECRET, REFRESH_TOKEN_SECRET } = process.env;
if (!WEB_CLIENT_ID || !ACCESS_TOKEN_SECRET || !REFRESH_TOKEN_SECRET) {
    throw new Error(
        'Any of the following environment variables are missing: WEB_CLIENT_ID, ACCESS_TOKEN_SECRET, REFRESH_TOKEN_SECRET'
    );
}

const client = new OAuth2Client(WEB_CLIENT_ID);

export function createLoginTokens(_id: String, email: String): LoginTokens {
    const payload = {
        _id,
        email,
    };
    const accessToken = jwt.sign(payload, ACCESS_TOKEN_SECRET!!, {
        expiresIn: '15m',
    });
    const refreshToken = jwt.sign(payload, REFRESH_TOKEN_SECRET!!, {
        expiresIn: '1h',
    });

    return { accessToken, refreshToken };
}

/**
 * @param {NextApiRequest} req The request to retrieve the token from.
 * @returns {TokenPayload | null} The token payload, or null if the token is invalid.
 */
export function verifyToken(req: NextApiRequest): TokenPayload | null {
    const { authorization } = req.headers;
    if (!authorization) return null;

    const [, token] = authorization.split(' ');
    if (!token) return null;

    try {
        const payload = jwt.verify(token, ACCESS_TOKEN_SECRET!!) as {
            _id: string;
            email: string;
        };
        return payload;
    } catch (err) {
        return null;
    }
}

/**
 * @param {NextApiRequest} req The request to retrieve the token from.
 * @returns {string | null} The new access token if the refresh token is valid.
 */
export function refreshToken(req: NextApiRequest): string | null {
    const payload = verifyToken(req);
    if (!payload) return null;

    const newAccessToken = jwt.sign(payload, ACCESS_TOKEN_SECRET!!, {
        expiresIn: '15m',
    });

    return newAccessToken;
}

/**
 * @param {string} token The token to verify.
 * @returns {Promise<GoogleTokenPayload | null>} The token payload, or null if the token is invalid.
 */
export async function verifyGoogleToken(token: string): Promise<GoogleTokenPayload | null> {
    try {
        const ticket = await client.verifyIdToken({
            idToken: token,
            audience: WEB_CLIENT_ID,
        });
        const payload = ticket.getPayload();

        if (!payload) return null;

        return {
            _id: payload['sub'],
            nombre: payload['given_name']!!,
            apellido: payload['family_name']!!,
            email: payload['email']!!,
            imagenUrl: payload['picture']!!,
        };
    } catch (err) {
        return null;
    }
}
