import os
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, declarative_base

# Use SQLite by default for local development; can be overridden via DATABASE_URL env var
DATABASE_URL = os.getenv("DATABASE_URL", "sqlite:///./opsai.db")

engine = create_engine(DATABASE_URL, echo=False, future=True, connect_args={"check_same_thread": False} if DATABASE_URL.startswith("sqlite") else {})
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

Base = declarative_base()
