import '../styles/globals.css';
import type { AppProps } from 'next/app';
import 'bootswatch/dist/cosmo/bootstrap.min.css';

function MyApp({ Component, pageProps }: AppProps) {
    return <Component {...pageProps} />;
}

export default MyApp;
