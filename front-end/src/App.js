import { Routes, Route } from 'react-router-dom';

import './App.css';
import Register from './pages/register'
import WriteArticle from './pages/writeArticle';

function App() {
  return (
    <div className="App">
        <h1>My App</h1>
        <Routes>
            <Route path='/register' element={<Register/>}/>
            <Route path='/article/write' element={<WriteArticle/>}/>
        </Routes>
    </div>
  );
}

export default App;
