from datetime import datetime
from sqlalchemy import Column, Integer, String, Boolean, DateTime, Enum, Text, ForeignKey
from sqlalchemy.orm import relationship
from .database import Base
import enum

class SeverityEnum(str, enum.Enum):
    CRITICAL = "Critical"
    HIGH = "High"
    MEDIUM = "Medium"
    LOW = "Low"

class StatusEnum(str, enum.Enum):
    INVESTIGATING = "Investigating"
    MITIGATING = "Mitigating"
    RESOLVED = "Resolved"
    CLOSED = "Closed"

class Incident(Base):
    __tablename__ = "incidents"
    id = Column(Integer, primary_key=True, index=True)
    incident_id = Column(String, unique=True, index=True)  # e.g., INC-2048
    title = Column(String, nullable=False)
    description = Column(Text)
    severity = Column(Enum(SeverityEnum), nullable=False)
    status = Column(Enum(StatusEnum), default=StatusEnum.INVESTIGATING, nullable=False)
    service = Column(String, nullable=False)
    team = Column(String, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow)
    updated_at = Column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow)
    logs = relationship("LogEntry", back_populates="incident", cascade="all, delete-orphan")

class LogEntry(Base):
    __tablename__ = "log_entries"
    id = Column(Integer, primary_key=True, index=True)
    incident_id = Column(Integer, ForeignKey("incidents.id"), nullable=False)
    timestamp = Column(DateTime, default=datetime.utcnow)
    level = Column(String)  # e.g., ERROR, WARN, INFO
    message = Column(Text)
    incident = relationship("Incident", back_populates="logs")
