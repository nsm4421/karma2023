import { Routes, Route } from 'react-router-dom';
import Nav from './components/nav/index';
import Post from './components/post/index';
import WritePost from './components/post/write/index';
import Register from './components/auth/register/index';
import Login from './components/auth/login/index';

const App = () => {
  return (
  <div className="App">
    <Nav/>
    <Routes>
      <Route path="/register" element={<Register/>}></Route>
      <Route path="/login" element={<Login/>}></Route>
      <Route path="/post" element={<Post/>}></Route>
      <Route path="/post/write" element={<WritePost/>}></Route>
    </Routes>
  </div>
  );
}

export default App;
