// 방명록
import React from 'react';
import Button from '../Button';

const Bio = (isAuthenticated) => {
    return (
        <div>
            <h3>자기소개</h3>    
            { isAuthenticated && <Button>수정</Button> }
        </div>
    );
};

export default Bio;