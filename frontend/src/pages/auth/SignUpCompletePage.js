import React from 'react';
import { Link } from 'react-router-dom';

const SignUpCompletePage = () => {
  return (
    <div>
      <h2>회원가입이 완료되었습니다!</h2>
      <p>로그인 페이지로 이동하여 로그인 해주세요.</p>
      <Link to="/login">
        <button>로그인 페이지로 이동</button>
      </Link>
    </div>
  );
};

export default SignUpCompletePage;