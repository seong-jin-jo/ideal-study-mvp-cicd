import React from 'react';
import styled from 'styled-components';

const StyledInput = styled.input`
//   width: 100%;
//   padding: 10px;
//   margin: 8px 0;
//   border: 1px solid #ccc;
//   border-radius: 5px;
//   font-size: 16px;

//   &:focus {
//     outline: none;
//     border-color: #007bff;
  }
`;

const Input = ({ type, value, onChange, placeholder }) => {
  return (
    <StyledInput
      type={type}
      value={value}
      onChange={onChange}
      placeholder={placeholder}
    />
  );
};

export default Input;