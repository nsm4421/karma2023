import { Routes, Route } from 'react-router-dom';

import './App.css';
import ModifyArticle from './pages/modifyArticle';
import Register from './pages/register'
import ShowArticle from './pages/showArticles';
import WriteArticle from './pages/writeArticle';

function App() {
  return (
    <div className="App">
        <h1>TEST</h1>
        <a href="/register">Reigster</a>
        <br/>
        <a href="/article/write">Write Article</a>
        <br/>
        <a href="/article">Show Article</a>
        <br/>
        <Routes>
            <Route path='/register' element={<Register/>}/>
            <Route exact path='/article/write' element={<WriteArticle/>}/>
            <Route exact path='/article/modify/:id' element={<ModifyArticle/>}/>
            <Route exact path='/article' element={<ShowArticle/>}/>
        </Routes>
    </div>
  );
}

export default App;
