import mongoose, { ConnectOptions } from 'mongoose';

const { MONGODB_URL } = process.env;
if (!MONGODB_URL) {
    throw new Error('MONGODB_URL ENVIRONMENT VARIABLE IS NOT SET');
}

/**
 * Global is used here to maintain a cached connection across hot reloads
 * in development. This prevents connections from growing exponentially
 * during API Route usage.
 */
let cached = global.mongo;
if (!cached) {
    cached = global.mongo = { conn: null, promise: null };
}

const getConnection = async (): Promise<mongoose.Mongoose> => {
    cached = cached!!;
    if (cached.conn) return cached.conn;

    if (!cached.promise) {
        const opts: ConnectOptions = {
            serverSelectionTimeoutMS: 5000,
        };
        cached.promise = mongoose.connect(MONGODB_URL, opts);
    }

    cached.conn = await cached.promise;

    return cached.conn;
};

export default getConnection;
