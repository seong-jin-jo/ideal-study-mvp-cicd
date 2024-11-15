import React from 'react';
import styled from 'styled-components';

const StyledButton = styled.button`

  padding: 5px 12px;
  font-size: 14px;
  color: #ffffff;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;

  &:hover {
    background-color: #0056b3;
  }

`;

const Button = ({ children, onClick, disabled }) => {
  return (
    <StyledButton onClick={onClick} disabled={disabled}>
      {children}
    </StyledButton>
  );
};

export default Button;