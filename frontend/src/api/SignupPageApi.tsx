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

