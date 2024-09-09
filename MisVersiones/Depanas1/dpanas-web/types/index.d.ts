import mongoose from 'mongoose';

declare global {
    var mongo: { conn: mongoose.Mongoose | null; promise: Promise<mongoose.Mongoose> | null } | undefined;
}
