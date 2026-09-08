from sqlalchemy.orm import Session
from . import models, schemas

def get_incident(db: Session, incident_id: int):
    return db.query(models.Incident).filter(models.Incident.id == incident_id).first()

def get_incidents(db: Session, skip: int = 0, limit: int = 100):
    return db.query(models.Incident).offset(skip).limit(limit).all()

def create_incident(db: Session, incident: schemas.IncidentCreate):
    db_incident = models.Incident(**incident.dict())
    db.add(db_incident)
    db.commit()
    db.refresh(db_incident)
    return db_incident

def update_incident(db: Session, incident_id: int, incident: schemas.IncidentUpdate):
    db_inc = get_incident(db, incident_id)
    if not db_inc:
        return None
    for field, value in incident.dict(exclude_unset=True).items():
        setattr(db_inc, field, value)
    db.commit()
    db.refresh(db_inc)
    return db_inc

def delete_incident(db: Session, incident_id: int):
    db_inc = get_incident(db, incident_id)
    if not db_inc:
        return None
    db.delete(db_inc)
    db.commit()
    return db_inc
