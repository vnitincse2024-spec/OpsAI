from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
import os

from .. import schemas, models
from ..database import SessionLocal
from ..ai.provider import OpenAIProvider, MockAIProvider, AIProvider

router = APIRouter()

def get_ai_provider() -> AIProvider:
    if os.getenv("OPENAI_API_KEY"):
        return OpenAIProvider()
    return MockAIProvider()

# Dependency for DB session
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@router.post('/analyze', response_model=schemas.AIAnalysisResponse)
def analyze_incident(request: schemas.AIAnalysisRequest, db: Session = Depends(get_db)):
    # Verify incident exists
    incident = db.query(models.Incident).filter(models.Incident.id == request.incident_id).first()
    if not incident:
        raise HTTPException(status_code=404, detail='Incident not found')
    provider = get_ai_provider()
    result = provider.analyze_incident(request.incident_id, request.logs)
    return schemas.AIAnalysisResponse(
        root_cause=result.root_cause,
        confidence=result.confidence,
        evidence=result.evidence,
        recommended_actions=result.recommended_actions,
    )
