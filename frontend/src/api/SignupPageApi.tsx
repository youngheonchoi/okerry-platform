export async function sendEmailVerification(email: string) {
    const res = await fetch("http://localhost:8080/auth/send-email", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ email }),
    });

    if (!res.ok) {
        throw new Error("메일 발송 실패");
    }
}

export async function sendEmailVerification2(email: string, code: String) {
    const res = await fetch("http://localhost:8080/auth/code-check", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, code }),
    });

    if (!res.ok) {
        throw new Error("메일 발송 실패");
    }
}

export async function signUp(email: string, password: String) {
    const res = await fetch("http://localhost:8080/auth/sign-up", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
    });

    if (!res.ok) {
        throw new Error("메일 발송 실패");
    }
}
