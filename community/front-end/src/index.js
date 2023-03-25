import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';
import {
    RecoilRoot,
    atom,
} from 'recoil';

export const userState = atom({
    key: 'user',
    default: {
        nickname:null
    },
});

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <BrowserRouter>
            <RecoilRoot>
                <App />
            </RecoilRoot>
        </BrowserRouter>
    </React.StrictMode>
);

reportWebVitals();
