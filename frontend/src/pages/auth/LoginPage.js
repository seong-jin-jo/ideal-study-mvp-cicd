import React, { useContext, useState } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { signUpUser } from '../../services/auth/UserService.mjs';

const LoginPage = () => {
  const { login } = useContext(AuthContext); // 로그인 함수 가져오기
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async() => {
    try{
      const response = await signUpUser(username, password);
      console.log('로그인 성공:', { response });
      login(); // context 를 로그인상태로 등록
      navigate('/'); // 로그인시 메인 페이지로 이동
    }catch(error) {
      console.log(error);
    }
  };

  return (
    <div>
      <h2>로그인</h2>
      <input
        type="text"
        placeholder="아이디"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        type="password"
        placeholder="비밀번호"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button onClick={handleLogin}>로그인</button>
    </div>
  );
};

export default LoginPage;