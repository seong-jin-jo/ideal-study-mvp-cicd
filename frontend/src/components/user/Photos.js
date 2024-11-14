// 사진첩(프로필사진)
import React from 'react';
import Button from '../Button';

const Photos = (user, isAuthenticated) => {
    return (
        <div>
            <h3>사진첩</h3>
            <img className="profile-avatar" src={user.avatar} alt={`${user.name} 프로필`} />
            <br/><br/>
            { isAuthenticated && <Button>수정</Button> }
        </div>
    );
};

export default Photos;