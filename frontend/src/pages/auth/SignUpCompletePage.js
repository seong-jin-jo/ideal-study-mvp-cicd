import React from 'react';
import { Link } from 'react-router-dom';
import Button from '../../components/Button';

const SignUpCompletePage = () => {
  return (
    <div>
      <h2>회원가입이 완료되었습니다!</h2>
      <p>이메일인증후 로그인 해주세요.</p>
      <Link to="/login">
        <Button>로그인 페이지로 이동</Button>
      </Link>
    </div>
  );
};

export default SignUpCompletePage;