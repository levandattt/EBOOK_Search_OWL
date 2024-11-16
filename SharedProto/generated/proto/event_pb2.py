# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: proto/event.proto
"""Generated protocol buffer code."""
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from google.protobuf import wrappers_pb2 as google_dot_protobuf_dot_wrappers__pb2


DESCRIPTOR = _descriptor.FileDescriptor(
  name='proto/event.proto',
  package='org.ebook_searching.proto',
  syntax='proto3',
  serialized_options=b'Z\034github.com/tqchu/SharedProto',
  create_key=_descriptor._internal_create_key,
  serialized_pb=b'\n\x11proto/event.proto\x12\x19org.ebook_searching.proto\x1a\x1egoogle/protobuf/wrappers.proto\"\xb5\x02\n\x0c\x41\x64\x64\x42ookEvent\x12\n\n\x02id\x18\x01 \x01(\x03\x12\r\n\x05title\x18\x02 \x01(\t\x12\x38\n\x06genres\x18\x03 \x03(\x0b\x32 .org.ebook_searching.proto.GenreR\x06genres\x12\x14\n\x0cpublished_at\x18\x04 \x01(\x03\x12\x11\n\tpublisher\x18\x05 \x01(\t\x12\x13\n\x0btotal_pages\x18\x06 \x01(\x05\x12\x10\n\x08language\x18\x07 \x01(\t\x12\x13\n\x0b\x64\x65scription\x18\x08 \x01(\t\x12\r\n\x05image\x18\t \x01(\t\x12;\n\x07\x61uthors\x18\n \x03(\x0b\x32!.org.ebook_searching.proto.AuthorR\x07\x61uthors\x12\x0c\n\x04uuid\x18\x0b \x01(\t\x12\x11\n\told_title\x18\x0c \x01(\t\"\x9a\x01\n\x05Genre\x12\n\n\x02id\x18\x01 \x01(\x03\x12*\n\x04name\x18\x02 \x01(\x0b\x32\x1c.google.protobuf.StringValue\x12+\n\x05image\x18\x03 \x01(\x0b\x32\x1c.google.protobuf.StringValue\x12\x0c\n\x04uuid\x18\x04 \x01(\t\x12\x10\n\x08old_name\x18\x05 \x01(\t\x12\x0c\n\x04slug\x18\x06 \x01(\t\"\xcd\x03\n\x06\x41uthor\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x0c\n\x04name\x18\x02 \x01(\t\x12\x30\n\nstage_name\x18\x03 \x01(\x0b\x32\x1c.google.protobuf.StringValue\x12\x30\n\nbirth_date\x18\x04 \x01(\x0b\x32\x1c.google.protobuf.StringValue\x12\x30\n\ndeath_date\x18\x05 \x01(\x0b\x32\x1c.google.protobuf.StringValue\x12\x31\n\x0b\x62irth_place\x18\x06 \x01(\x0b\x32\x1c.google.protobuf.StringValue\x12\x31\n\x0bnationality\x18\x07 \x01(\x0b\x32\x1c.google.protobuf.StringValue\x12-\n\x07website\x18\x08 \x01(\x0b\x32\x1c.google.protobuf.StringValue\x12\x31\n\x0b\x64\x65scription\x18\t \x01(\x0b\x32\x1c.google.protobuf.StringValue\x12+\n\x05image\x18\n \x01(\x0b\x32\x1c.google.protobuf.StringValue\x12\x0c\n\x04uuid\x18\x0b \x01(\t\x12\x10\n\x08old_name\x18\x0c \x01(\tB\x1eZ\x1cgithub.com/tqchu/SharedProtob\x06proto3'
  ,
  dependencies=[google_dot_protobuf_dot_wrappers__pb2.DESCRIPTOR,])




_ADDBOOKEVENT = _descriptor.Descriptor(
  name='AddBookEvent',
  full_name='org.ebook_searching.proto.AddBookEvent',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='id', full_name='org.ebook_searching.proto.AddBookEvent.id', index=0,
      number=1, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='title', full_name='org.ebook_searching.proto.AddBookEvent.title', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='genres', full_name='org.ebook_searching.proto.AddBookEvent.genres', index=2,
      number=3, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, json_name='genres', file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='published_at', full_name='org.ebook_searching.proto.AddBookEvent.published_at', index=3,
      number=4, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='publisher', full_name='org.ebook_searching.proto.AddBookEvent.publisher', index=4,
      number=5, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='total_pages', full_name='org.ebook_searching.proto.AddBookEvent.total_pages', index=5,
      number=6, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='language', full_name='org.ebook_searching.proto.AddBookEvent.language', index=6,
      number=7, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='description', full_name='org.ebook_searching.proto.AddBookEvent.description', index=7,
      number=8, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='image', full_name='org.ebook_searching.proto.AddBookEvent.image', index=8,
      number=9, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='authors', full_name='org.ebook_searching.proto.AddBookEvent.authors', index=9,
      number=10, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, json_name='authors', file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='uuid', full_name='org.ebook_searching.proto.AddBookEvent.uuid', index=10,
      number=11, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='old_title', full_name='org.ebook_searching.proto.AddBookEvent.old_title', index=11,
      number=12, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=81,
  serialized_end=390,
)


_GENRE = _descriptor.Descriptor(
  name='Genre',
  full_name='org.ebook_searching.proto.Genre',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='id', full_name='org.ebook_searching.proto.Genre.id', index=0,
      number=1, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='name', full_name='org.ebook_searching.proto.Genre.name', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='image', full_name='org.ebook_searching.proto.Genre.image', index=2,
      number=3, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='uuid', full_name='org.ebook_searching.proto.Genre.uuid', index=3,
      number=4, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='old_name', full_name='org.ebook_searching.proto.Genre.old_name', index=4,
      number=5, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='slug', full_name='org.ebook_searching.proto.Genre.slug', index=5,
      number=6, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=393,
  serialized_end=547,
)


_AUTHOR = _descriptor.Descriptor(
  name='Author',
  full_name='org.ebook_searching.proto.Author',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='id', full_name='org.ebook_searching.proto.Author.id', index=0,
      number=1, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='name', full_name='org.ebook_searching.proto.Author.name', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='stage_name', full_name='org.ebook_searching.proto.Author.stage_name', index=2,
      number=3, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='birth_date', full_name='org.ebook_searching.proto.Author.birth_date', index=3,
      number=4, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='death_date', full_name='org.ebook_searching.proto.Author.death_date', index=4,
      number=5, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='birth_place', full_name='org.ebook_searching.proto.Author.birth_place', index=5,
      number=6, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='nationality', full_name='org.ebook_searching.proto.Author.nationality', index=6,
      number=7, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='website', full_name='org.ebook_searching.proto.Author.website', index=7,
      number=8, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='description', full_name='org.ebook_searching.proto.Author.description', index=8,
      number=9, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='image', full_name='org.ebook_searching.proto.Author.image', index=9,
      number=10, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='uuid', full_name='org.ebook_searching.proto.Author.uuid', index=10,
      number=11, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='old_name', full_name='org.ebook_searching.proto.Author.old_name', index=11,
      number=12, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=550,
  serialized_end=1011,
)

_ADDBOOKEVENT.fields_by_name['genres'].message_type = _GENRE
_ADDBOOKEVENT.fields_by_name['authors'].message_type = _AUTHOR
_GENRE.fields_by_name['name'].message_type = google_dot_protobuf_dot_wrappers__pb2._STRINGVALUE
_GENRE.fields_by_name['image'].message_type = google_dot_protobuf_dot_wrappers__pb2._STRINGVALUE
_AUTHOR.fields_by_name['stage_name'].message_type = google_dot_protobuf_dot_wrappers__pb2._STRINGVALUE
_AUTHOR.fields_by_name['birth_date'].message_type = google_dot_protobuf_dot_wrappers__pb2._STRINGVALUE
_AUTHOR.fields_by_name['death_date'].message_type = google_dot_protobuf_dot_wrappers__pb2._STRINGVALUE
_AUTHOR.fields_by_name['birth_place'].message_type = google_dot_protobuf_dot_wrappers__pb2._STRINGVALUE
_AUTHOR.fields_by_name['nationality'].message_type = google_dot_protobuf_dot_wrappers__pb2._STRINGVALUE
_AUTHOR.fields_by_name['website'].message_type = google_dot_protobuf_dot_wrappers__pb2._STRINGVALUE
_AUTHOR.fields_by_name['description'].message_type = google_dot_protobuf_dot_wrappers__pb2._STRINGVALUE
_AUTHOR.fields_by_name['image'].message_type = google_dot_protobuf_dot_wrappers__pb2._STRINGVALUE
DESCRIPTOR.message_types_by_name['AddBookEvent'] = _ADDBOOKEVENT
DESCRIPTOR.message_types_by_name['Genre'] = _GENRE
DESCRIPTOR.message_types_by_name['Author'] = _AUTHOR
_sym_db.RegisterFileDescriptor(DESCRIPTOR)

AddBookEvent = _reflection.GeneratedProtocolMessageType('AddBookEvent', (_message.Message,), {
  'DESCRIPTOR' : _ADDBOOKEVENT,
  '__module__' : 'proto.event_pb2'
  # @@protoc_insertion_point(class_scope:org.ebook_searching.proto.AddBookEvent)
  })
_sym_db.RegisterMessage(AddBookEvent)

Genre = _reflection.GeneratedProtocolMessageType('Genre', (_message.Message,), {
  'DESCRIPTOR' : _GENRE,
  '__module__' : 'proto.event_pb2'
  # @@protoc_insertion_point(class_scope:org.ebook_searching.proto.Genre)
  })
_sym_db.RegisterMessage(Genre)

Author = _reflection.GeneratedProtocolMessageType('Author', (_message.Message,), {
  'DESCRIPTOR' : _AUTHOR,
  '__module__' : 'proto.event_pb2'
  # @@protoc_insertion_point(class_scope:org.ebook_searching.proto.Author)
  })
_sym_db.RegisterMessage(Author)


DESCRIPTOR._options = None
# @@protoc_insertion_point(module_scope)