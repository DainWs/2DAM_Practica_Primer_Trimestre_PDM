# USER MANUAL - FINAL PRACTICE FIRST QUARTER, PDM
- STUDENT: JOSE ANTONIO DUARTE PÉREZ
- APPLICATION: Showroom)
- IMPORTANT DATA:
The database uses CASCADE DELETE, so, if a exhibition comment is created on one exhibition,
and we delete the exhibition, then the comment will also be deleted
(also the records of the EXPOSES table that are related to this EXPOSITION). 

The data for CASCADE DELETE are the following:

delete one EXPOSURE:

1. It will delete the records from the EXPOSES table that are related to this EXPOSURE.
2. Will delete comments on this EXHIBITION.

delete one COMMENT:

1. Only the comment will be deleted.

delete one JOB:

1. Will delete comments on this JOB.

delete one ARTIST:

1. It will delete the works of this artist.
2. You will delete comments related to the works of this artist.
3. It will delete the records from the EXPOSES table that are related to this ARTIST.

deleting one recod exposes:

Only the Expone record will be deleted.

When you login for the first time, a login window appears to you, in which
you must fill in the fields to save a password for next time sign in.