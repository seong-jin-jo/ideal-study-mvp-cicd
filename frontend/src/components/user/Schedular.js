// 스케쥴러

import React from 'react';
import Button from '../Button';

const Schedular = (isAuthenticated) => {
    return (
        <div>
            <h3>스케쥴러</h3>
            { isAuthenticated && <Button>수정</Button> }
        </div>
    );
};

export default Schedular;