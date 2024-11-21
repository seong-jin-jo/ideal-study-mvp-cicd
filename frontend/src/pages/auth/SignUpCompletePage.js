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

      {/* 디버깅 */}
      <br/><br/>
      --- 이메일 클릭시 이 페이지로 이동하며 임시비밀번호가 보임 ----
      <h3>임시비밀번호: { }</h3>
    </div>
  );
};

export default SignUpCompletePage;