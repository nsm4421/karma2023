import { Routes, Route } from 'react-router-dom';
import Nav from './components/nav/Nav';
import PostList from './components/post/PostList';
import WritePost from './components/post/WritePost';
import Register from './components/auth/Register';
import Login from './components/auth/Login';

const App = () => {
  return (
  <div className="App">
    <Nav/>
    <Routes>
      <Route path="/register" element={<Register/>}></Route>
      <Route path="/login" element={<Login/>}></Route>
      <Route path="/post" element={<PostList/>}></Route>
      <Route path="/post/write" element={<WritePost/>}></Route>
    </Routes>
  </div>
  );
}

export default App;
