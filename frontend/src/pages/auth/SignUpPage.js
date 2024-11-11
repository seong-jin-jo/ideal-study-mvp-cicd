import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const SignUpPage = () => {
  const [username, setUsername] = useState('');
  const [userEmail, setUserEmail] = useState('');
  const navigate = useNavigate();

  const handleSignUp = () => {
    console.log('회원가입 성공:', { username, userEmail });
    alert('회원가입이 완료되었습니다.');
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
      <input
        type="text"
        placeholder="이름"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <br/>
      개인정보 수집 및 이용약관 동의 <input type="checkbox"/>
      <br/><button onClick={handleSignUp}>회원가입</button>
    </div>
  );
};

export default SignUpPage;