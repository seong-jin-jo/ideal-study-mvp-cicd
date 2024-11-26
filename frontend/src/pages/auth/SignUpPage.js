import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../../components/Button';
import { signUpUser } from '../../services/UserService.mjs';

const SignUpPage = () => {
  // const [username, setUsername] = useState('');
  const [userEmail, setUserEmail] = useState('');
  const navigate = useNavigate();

  const handleSignUp = async() => {
    await signUpUser(userEmail);
    navigate('/signup-complete'); // 회원가입 완료 페이지로 이동
  };

  return (
    <div>
      <h2>회원가입</h2>
      <input
        type="email"
        placeholder="이메일"
        value={userEmail}
        onChange={(e) => setUserEmail(e.target.value)}
      />
      {/* 회원가입시 이름은 받지 않음 */}
      {/* <input
        type="text"
        placeholder="이름"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      /> */}
      <br/>
      개인정보 수집 및 이용약관 동의 <input type="checkbox"/>
      <br/>
      <Button onClick={handleSignUp}>회원가입</Button>
    </div>
  );
};

export default SignUpPage;