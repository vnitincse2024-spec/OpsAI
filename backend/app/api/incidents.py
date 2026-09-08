from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from typing import List

from .. import schemas, models
from ..database import SessionLocal

router = APIRouter()

# Dependency
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@router.get('/', response_model=List[schemas.IncidentOut])
def list_incidents(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    return db.query(models.Incident).offset(skip).limit(limit).all()

@router.post('/', response_model=schemas.IncidentOut, status_code=status.HTTP_201_CREATED)
def create_incident(incident: schemas.IncidentCreate, db: Session = Depends(get_db)):
    db_incident = models.Incident(
        incident_id=incident.incident_id,
        title=incident.title,
        description=incident.description,
        severity=incident.severity,
        status=incident.status,
        service=incident.service,
        team=incident.team,
    )
    db.add(db_incident)
    db.commit()
    db.refresh(db_incident)
    return db_incident

@router.get('/{incident_id}', response_model=schemas.IncidentOut)
def get_incident(incident_id: str, db: Session = Depends(get_db)):
    incident = db.query(models.Incident).filter(models.Incident.incident_id == incident_id).first()
    if not incident:
        raise HTTPException(status_code=404, detail='Incident not found')
    return incident

@router.put('/{incident_id}', response_model=schemas.IncidentOut)
def update_incident(incident_id: str, incident_update: schemas.IncidentUpdate, db: Session = Depends(get_db)):
    incident = db.query(models.Incident).filter(models.Incident.incident_id == incident_id).first()
    if not incident:
        raise HTTPException(status_code=404, detail='Incident not found')
    for var, value in incident_update.dict(exclude_unset=True).items():
        setattr(incident, var, value)
    db.commit()
    db.refresh(incident)
    return incident

@router.delete('/{incident_id}', status_code=status.HTTP_204_NO_CONTENT)
def delete_incident(incident_id: str, db: Session = Depends(get_db)):
    incident = db.query(models.Incident).filter(models.Incident.incident_id == incident_id).first()
    if not incident:
        raise HTTPException(status_code=404, detail='Incident not found')
    db.delete(incident)
    db.commit()
    return None
