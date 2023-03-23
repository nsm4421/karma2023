import { Routes, Route } from 'react-router-dom';

import ModifyArticle from './pages/modifyArticle';
import ShowArticle from './pages/showArticles';
import WriteArticle from './pages/writeArticle';
import Nav from './components/nav';
import Register from './pages/register';

function App() {
  return (
    <div className="App">
        <Nav/>
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
