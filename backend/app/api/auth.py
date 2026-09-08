from fastapi import APIRouter, Depends, HTTPException, status
from pydantic import BaseModel, EmailStr
from datetime import datetime, timedelta
import os
import jwt

router = APIRouter()

# Simple in-memory user for demo purposes
FAKE_USER = {
    "email": "admin@opsai.com",
    "password": "password123",
    "full_name": "OpsAI Admin",
    "id": 1,
}

class Token(BaseModel):
    access_token: str
    token_type: str = "bearer"

class LoginRequest(BaseModel):
    email: EmailStr
    password: str

def create_access_token(data: dict, expires_delta: timedelta | None = None):
    to_encode = data.copy()
    expire = datetime.utcnow() + (expires_delta or timedelta(minutes=30))
    to_encode.update({"exp": expire})
    secret = os.getenv("JWT_SECRET", "opsai-secret")
    algorithm = "HS256"
    return jwt.encode(to_encode, secret, algorithm=algorithm)

@router.post("/login", response_model=Token)
def login(req: LoginRequest):
    if req.email != FAKE_USER["email"] or req.password != FAKE_USER["password"]:
        raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Invalid credentials")
    token = create_access_token({"sub": str(FAKE_USER["id"])})
    return Token(access_token=token)
