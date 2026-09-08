import os
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from fastapi.middleware.gzip import GZipMiddleware

# Import routers (only those that exist)
from .api import auth, incidents, ai, notifications
# from .api import auth, incidents, ai, analytics, knowledge, admin, notifications

app = FastAPI(title="OpsAI Backend", version="0.1.0")

# CORS configuration (allow frontend dev server)
origins = [
    "http://localhost",
    "http://localhost:5173",
    os.getenv("FRONTEND_ORIGIN", "http://localhost:5173"),
]
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Optional gzip compression for responses
app.add_middleware(GZipMiddleware, minimum_size=1000)

# Include routers
app.include_router(auth.router, prefix="/api/auth", tags=["auth"])
app.include_router(incidents.router, prefix="/api/incidents", tags=["incidents"])
app.include_router(ai.router, prefix="/api/ai", tags=["ai"])

@app.get("/health", tags=["health"])
async def health_check():
    return {"status": "ok"}
