from pydantic import BaseModel, Field
from enum import Enum
from typing import List, Optional

class SeverityEnum(str, Enum):
    CRITICAL = "Critical"
    HIGH = "High"
    MEDIUM = "Medium"
    LOW = "Low"

class StatusEnum(str, Enum):
    INVESTIGATING = "Investigating"
    MITIGATING = "Mitigating"
    RESOLVED = "Resolved"
    CLOSED = "Closed"

class IncidentBase(BaseModel):
    incident_id: str = Field(..., example="INC-2048")
    title: str
    description: Optional[str] = None
    severity: SeverityEnum
    status: Optional[StatusEnum] = StatusEnum.INVESTIGATING
    service: str
    team: str

class IncidentCreate(IncidentBase):
    pass

class IncidentUpdate(BaseModel):
    title: Optional[str]
    description: Optional[str]
    severity: Optional[SeverityEnum]
    status: Optional[StatusEnum]
    service: Optional[str]
    team: Optional[str]

class IncidentOut(IncidentBase):
    id: int
    created_at: str
    updated_at: str

    class Config:
        orm_mode = True

class LogEntryBase(BaseModel):
    level: str
    message: str

class LogEntryCreate(LogEntryBase):
    incident_id: int

class LogEntryOut(LogEntryBase):
    id: int
    timestamp: str
    incident_id: int

    class Config:
        orm_mode = True

class AIAnalysisRequest(BaseModel):
    incident_id: int
    logs: List[LogEntryBase]

class AIAnalysisResponse(BaseModel):
    root_cause: str
    confidence: float
    evidence: List[str]
    recommended_actions: List[str]
