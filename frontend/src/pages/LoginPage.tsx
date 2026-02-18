import React, { FC, useState, ChangeEvent } from "react";
import { useNavigate } from 'react-router-dom';
import "../assets/css/auth.css";
import {actionLogin} from "../api/LoginPageApi";

const LoginPage: FC = () => {
    const [id, setId] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    let navigate = useNavigate();

    const handleLogin = async (): Promise<void> => {
        console.log("Login:", { id, password });

        try{
            await actionLogin(id, password);
        }catch (err){

        }
    };

    return (
        <div className="auth-container">
            <div className="auth-box">
                <h1 className="auth-title">Okerry Platform SSO</h1>

                <input
                    type="text"
                    placeholder="아이디"
                    className="auth-input"
                    value={id}
                    onChange={(e: ChangeEvent<HTMLInputElement>) =>
                        setId(e.target.value)
                    }
                />

                <input
                    type="password"
                    placeholder="비밀번호"
                    className="auth-input"
                    value={password}
                    onChange={(e: ChangeEvent<HTMLInputElement>) =>
                        setPassword(e.target.value)
                    }
                />

                <button
                    className="auth-button primary"
                    onClick={handleLogin}
                >
                    로그인
                </button>

                <button
                    className="auth-button secondary"
                    onClick={() => {
                        navigate("/signup")
                    }}
                >
                    회원가입
                </button>
            </div>
        </div>
    );
};

export default LoginPage;
