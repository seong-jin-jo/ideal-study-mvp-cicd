// 방명록
import React from 'react';
import Button from '../Button';

const GuestBook = ({isAuthenticated}) => {
    return (
        <div>
            <h3>방명록</h3>    
            { isAuthenticated && <Button>수정</Button> }
        </div>
    );
};

export default GuestBook;